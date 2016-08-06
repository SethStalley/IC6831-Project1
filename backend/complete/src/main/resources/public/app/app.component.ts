import {Component} from '@angular/core';
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
  public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;

  //Constructor for file upload
  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;

  }

}

