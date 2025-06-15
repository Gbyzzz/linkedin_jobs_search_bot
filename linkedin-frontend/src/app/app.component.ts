import { Component } from '@angular/core';
import {HeaderComponent} from './view/header/header.component';
import {Router, RouterOutlet} from '@angular/router';
import {FooterComponent} from './view/footer/footer.component';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [RouterOutlet, HeaderComponent, NgIf],
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(public router: Router) {

  }

  shouldShowHeaderFooter(): boolean {
    const hiddenRoutes = ['/login'];
    return !hiddenRoutes.includes(this.router.url);
  }}
