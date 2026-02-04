import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KnowledgeUploadComponent } from './features/knowledge-upload/knowledge-upload.component';
import { NavBarComponent } from "./shared/nav-bar/nav-bar.component";
import { FooterComponent } from "./shared/footer/footer.component";
import { HomeComponent } from "./shared/home/home.component";
import { RegisterComponent } from "./shared/register/register.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NavBarComponent, FooterComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
