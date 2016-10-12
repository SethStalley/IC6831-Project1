import {Component, Input} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';

@Component({
  selector: 'show-result',
  templateUrl: 'app/show-result.html',
  directives: [NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class ShowResult {
  fileName:string;

  public setFileName(fileName:string) {
    this.fileName = fileName;
  }
}

