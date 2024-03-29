/*
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpErrorHandlerService {
  constructor() {}

  handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // Handle CORS-related errors
      console.error('CORS issue:', error.message);
      // You can implement custom logic here to handle CORS errors
      return throwError('CORS issue: Unable to access the requested resource due to CORS restrictions.');
    } else {
      // Handle other HTTP errors
      console.error('HTTP error:', error.message);
      return throwError('HTTP Error: ' + error.message);
    }
  }
}
*/
