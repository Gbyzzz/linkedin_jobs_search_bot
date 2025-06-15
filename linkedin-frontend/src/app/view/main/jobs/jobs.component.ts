import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {SavedJob} from '../../../model/SavedJob';
import {SavedJobServiceImpl} from '../../../service/entity/impl/SavedJobServiceImpl';
import {TokenStorageService} from '../../../service/auth/token-storage.service';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {Pagination, SortDirection} from '../../../model/pagination/Pagination';

@Component({
  selector: 'app-jobs',
  imports: [NgForOf, NgIf, MatPaginator, DatePipe],
  templateUrl: './jobs.component.html',
  standalone: true,
  styleUrl: './jobs.component.scss'
})
export class JobsComponent implements OnInit {

  readonly defaultPageSize = 15;
  readonly defaultPageNumber = 0;
  readonly defaultSortDirection = SortDirection.ASC;

  protected savedJobs: SavedJob[] = [];
  protected originalSavedJobs: SavedJob[] = [];
  protected replyStates: string[] = ["NEW_JOB", "APPLIED", "REJECTED", "DELETED"];
  protected selectedReplyState: string = "ALL";
  protected isChanged: boolean = false;
  protected totalSavedJobsFound: number = 0;
  protected pagination: Pagination = new Pagination(this.defaultPageSize, this.defaultPageNumber, this.defaultSortDirection);


  constructor(private savedJobsService: SavedJobServiceImpl,
              private tokenStorage: TokenStorageService,
              private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.savedJobsService.findSavedJobsByUserProfile(this.tokenStorage.getUser()?.chatId, "all", this.pagination)
      .subscribe((results) => {
        this.originalSavedJobs = results.content;
        this.totalSavedJobsFound = results.totalElements;
        this.savedJobs = JSON.parse(JSON.stringify(this.originalSavedJobs));
      });
  }

  onReplyStateChange($event: any) {
    console.log($event.target.value);
    this.selectedReplyState = $event.target.value;
    this.pagination.pageNumber = this.defaultPageNumber;
    this.pagination.pageSize = this.defaultPageSize;
    this.getJobs();
  }

  openLink(jobId: number) {
    window.open("https://www.linkedin.com/jobs/view/" + jobId);
    this.savedJobsService.findFullJobById(jobId).subscribe((job) => {
      console.log(job);
    })
  }

  onApplyChange(id: number, $event: any) {
    let job = this.savedJobs.find(job => job.id === id);
    if (job) {
      if ($event.target.checked) {
        job.replyState = "APPLIED";
      } else {
        job.replyState = "NEW_JOB";
      }
      this.checkForChanges();
    }
  }

  onDeleteChange(id: number, $event: any) {
    let job = this.savedJobs.find(job => job.id === id);
    if (job) {
      if ($event.target.checked) {
        job.replyState = "DELETED";
      } else {
        job.replyState = "NEW_JOB";
      }
      this.checkForChanges();

    }
  }

  onRejectChange(id: number, $event: any) {
    let job = this.savedJobs.find(job => job.id === id);
    if (job) {
      if ($event.target.checked) {
        job.replyState = "REJECTED";
      } else {
        job.replyState = "APPLIED";
      }
      this.checkForChanges();

    }
  }

  allAppliedChecked(): boolean {
    return this.savedJobs.every(job => job.replyState === "APPLIED");
  }

  allDeletedChecked(): boolean {
    return this.savedJobs.every(job => job.replyState === "DELETED");
  }

  allRejectedChecked(): boolean {
    return this.savedJobs.every(job => job.replyState === "REJECTED");
  }

  onAllApplyChange($event: any) {
    this.savedJobs.forEach(job => {
      if ($event.target.checked) {
        if (job.replyState === "NEW_JOB") {
          job.replyState = "APPLIED";
        }
      } else {
        if (job.replyState === "APPLIED") {
          job.replyState = "NEW_JOB";
        }
      }
    });
    this.checkForChanges();

  }

  onAllDeleteChange($event: any) {
    this.savedJobs.forEach(job => {
      if ($event.target.checked) {
        if (job.replyState === "NEW_JOB") {
          job.replyState = "DELETED";
        }
      } else {
        if (job.replyState === "DELETED") {
          job.replyState = "NEW_JOB";
        }
      }
    });
    this.checkForChanges();

  }

  onAllRejectChange($event: any) {
    this.savedJobs.forEach(job => {
      if ($event.target.checked) {
        if (job.replyState === "APPLIED") {
          job.replyState = "REJECTED";
        }
      } else {
        if (job.replyState === "REJECTED") {
          job.replyState = "APPLIED";
        }
      }
    });
    this.checkForChanges();

  }

  checkForChanges() {
    if (this.savedJobs.length !== this.originalSavedJobs.length) {
      this.isChanged = true;
    }

    this.isChanged = !this.savedJobs.every((job, index) => {
      return job.replyState === this.originalSavedJobs[index].replyState;
    });
    this.cdr.detectChanges();

  }

  saveChanges() {
    if (window.confirm("are you sure?")) {

      this.savedJobsService.saveAll(this.savedJobs).subscribe((results) => {
        this.originalSavedJobs = results;
        this.savedJobs = JSON.parse(JSON.stringify(this.originalSavedJobs));
        this.getJobs();
        this.checkForChanges();
      });
      console.log("saved")
    }
  }

  revertChanges() {
    if (window.confirm("Are you sure?")) {
      this.savedJobs = JSON.parse(JSON.stringify(this.originalSavedJobs));
      this.checkForChanges();
    }
  }

  pageChanged(pageEvent: PageEvent) {
    if (this.pagination.pageSize != pageEvent.pageSize) {
      this.pagination.pageNumber = 0;
    } else {
      this.pagination.pageNumber = pageEvent.pageIndex;
    }

    this.pagination.pageSize = pageEvent.pageSize;
    this.getJobs();
  }

  getJobs() {
    // @ts-ignore
    this.savedJobsService.findSavedJobsByUserProfile(this.tokenStorage.getUser()?.chatId,
      this.selectedReplyState.toLowerCase(), this.pagination)
      .subscribe((results) => {
        console.log(results);
        this.originalSavedJobs = results.content;
        this.totalSavedJobsFound = results.totalElements;
        this.savedJobs = JSON.parse(JSON.stringify(this.originalSavedJobs));
        this.checkForChanges();
      });
  }
}
