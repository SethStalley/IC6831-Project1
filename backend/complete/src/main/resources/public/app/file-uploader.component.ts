import { bootstrap }    from '@angular/platform-browser-dynamic';
import {Component, Input} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from 'ng2-file-upload';

// static URL to send out file to;
const URL = 'http://localhost:8080/postFile';

@Component({
  selector: 'file-upload',
  templateUrl: 'app/file-uploader.html',
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class FileUploder {
  fileName:string;  
  public uploader:FileUploader = new FileUploader({url: URL, method: 'showVide'});
  public hasBaseDropZoneOver:boolean = false;

  showVideo() {
    this.setFileName('test.mp4');
  }

  fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }



  setFileName(fileName:string) {
    this.fileName = fileName;
  }

}

