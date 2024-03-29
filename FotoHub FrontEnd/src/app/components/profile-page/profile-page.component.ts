import { Component, OnInit, OnDestroy } from '@angular/core';
import { SignUp } from 'src/app/interfaces/auth.interface';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit, OnDestroy {
  user: SignUp | undefined;
  error: string | undefined;
  photos: any[] = [];
  userPhotos: any;
  private subscription: Subscription | undefined;

  constructor(private authService: AuthService, private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchUserData();

  }

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks when component is destroyed
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  logout() {
    this.authService.logout();
  }

  fetchUserData(): void {
    // Fetch current user data
    this.subscription = this.authService.currentLoggedUsed()?.subscribe({
      next: (response: SignUp | SignUp[]) => {
        if (Array.isArray(response)) {
          // If the response is an array, extract the first element
          if (response.length > 0) {
            this.user = response[0];
            console.log('User Data:', this.user);
            this.fetchUserPhotos(this.user);
          } else {
            console.error('Response array is empty.');
          }
        } else {
          // If the response is not an array, directly assign it to user
          this.user = response;
          console.log('User Data:', this.user);
        }
      },
      error: (error) => {
        // Handle error
        console.error('Error fetching user data:', error);
      }
    });

  }




  addPhoto(event: any): void {
    const selectedFile: File = event.target.files[0];
    if (!selectedFile) {
      console.error('No file selected.');
      return;
    }

    // Fetch the token and user email from localStorage
    const token = localStorage.getItem('token');
    const userEmail = localStorage.getItem('email');

    // Check if token and user email are available
    if (!token || !userEmail) {
      console.error('Authorization token or user email not found.');
      return;
    }

    // Prepare the form data
    const formData = new FormData();
    formData.append('upload', selectedFile);

    // Set the authorization header with token and user email
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'email': userEmail
    });

    // Make a POST request to the backend endpoint
    this.http.post<any>(`${environment.srvr}foto`, formData, { headers })
      .subscribe({
        next: (response: any) => {
          console.log('Photo uploaded successfully:', response);
          // Provide feedback to the user
          alert('Photo uploaded successfully!');
          this.fetchUserPhotos(this.user);
        },
        error: (error) => {
          console.error('Error uploading photo:', error);
          // Provide feedback to the user
          alert('Failed to upload photo. Please try again.');
        }
      });
  }



  fetchUserPhotos(user: SignUp | undefined): void {
    // Replace 'userID' with the actual user ID
    console.log(user);
    this.user = user;
    const userID = this.user?.id;

    if (!userID) {
      console.error('User ID not available.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Authorization token not found.');
      return;
    }

    // Set the authorization header
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<any>(`${environment.srvr}foto/user/${userID}`, { headers })
      .subscribe({
        next: (response) => {
          console.log(response.response);

          // Assuming response.response contains the array of photos
          this.userPhotos = response.response.content;
        },
        error: (error) => {
          console.error('Error fetching user photos:', error);
        }
      });
  }
}
