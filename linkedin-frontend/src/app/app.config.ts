import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import {routes} from './app.routes';
import {provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {SEARCH_PARAMETERS_URL_TOKEN} from './service/entity/impl/SearchParamsServiceImpl';
import {SAVED_JOB_URL_TOKEN} from './service/entity/impl/SavedJobServiceImpl';
import {environment} from '../environments/environment.dev';
import {USER_PROFILE_URL_TOKEN} from './service/entity/impl/UserProfileServiceImpl';
import {authInterceptorProviders} from './service/auth/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    authInterceptorProviders,
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    CommonModule,
    {
      provide: SEARCH_PARAMETERS_URL_TOKEN,
      useValue: environment.API_URL + 'search_parameter'
    },
    {
      provide: SAVED_JOB_URL_TOKEN,
      useValue: environment.API_URL + 'jobs'
    },
    {
      provide: USER_PROFILE_URL_TOKEN,
      useValue: environment.API_URL + 'users'
    }
  ]
};
