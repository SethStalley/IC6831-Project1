import { bootstrap }    from '@angular/platform-browser-dynamic';
import {Component, Input} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from 'ng2-file-upload';

// static URL to send out file to;
const URL = 'http://localhost:8080/postFile';
const GROUND_TRUTH_URL = 'http://localhost:8080/postGroundTruth';

@Component({
  selector: 'file-upload',
  templateUrl: 'app/file-uploader.html',
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class FileUploder {
  fileName:string;  
  public uploader:FileUploader = new FileUploader({url: URL, method: 'showVide'});
  public groundTruthUploader:FileUploader = new FileUploader({url: GROUND_TRUTH_URL});
  public hasBaseDropZoneOver:boolean = false;

  constructor() {
     this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
          var fileName : string = item.some['name'];
          console.log(fileName);
          this.setFileName(fileName)
     };
  }

  fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }
  setFileName(fileName:string) {
    this.fileName = fileName;
  }

}
