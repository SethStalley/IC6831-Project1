import {Component} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';

@Component({
  selector: 'my-app',
  directives: [NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES],
  template: `
            <h1 class="text-center">Identifique los Equipos</h1>
            `,
})

export class App {};

