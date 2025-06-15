import {Component, ElementRef, Inject, OnInit, Renderer2, ViewChild} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {AuthService} from '../../service/auth/auth.service';
import {LoginSharedService} from '../../service/auth/login-shared.service';
import {TokenStorageService} from '../../service/auth/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  @ViewChild('telegramBtn', {static: true}) telegramBtnRef!: ElementRef;

  constructor(
    private renderer2: Renderer2,
    @Inject(DOCUMENT) private doc: Document,
    private authService: AuthService,
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {
  }

  ngOnInit(): void {
    // Define the Telegram callback on the window object
    (window as any).onTelegramAuth = (user: any) => {
      console.log(user);
      this.authService.telegramLogin(user).subscribe(data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data.user);
         this.router.navigate(['/']).then();
      });
    };

    const script = this.renderer2.createElement('script');
    script.src = 'https://telegram.org/js/telegram-widget.js?22';
    script.setAttribute('data-telegram-login', 'gbyzzz_dev_bot');
    script.setAttribute('data-size', 'large');
    script.setAttribute('data-onauth', 'onTelegramAuth(user)');
    script.setAttribute('data-request-access', 'write');
    script.async = true;

    this.renderer2.appendChild(this.telegramBtnRef.nativeElement, script);
  }

}
