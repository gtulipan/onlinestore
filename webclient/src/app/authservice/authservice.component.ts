import { Component } from '@angular/core';
import { TranslationService } from '../translation.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-authservice',
  imports: [],
  standalone: true,
  templateUrl: './authservice.component.html',
  styleUrl: './authservice.component.css'
})
export class AuthserviceComponent {
  constructor(public translationService: TranslationService) {}
}
