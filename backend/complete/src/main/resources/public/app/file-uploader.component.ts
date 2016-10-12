import {Component, Input} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from 'ng2-file-upload';
//import {ToastyService, Toasty, ToastOptions, ToastData} from 'ng2-toasty';

// static URL to send out file to;
const URL = 'http://localhost:8080/postFile';

@Component({
  selector: 'file-upload',
  templateUrl: 'app/file-uploader.html',
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class FileUploder {
  public uploader:FileUploader = new FileUploader({url:URL});
  public hasBaseDropZoneOver:boolean = false;

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  constructor() {

  }


  // constructor(private toastyService:ToastyService) {
  //    this.uploader.onCompleteItem = (item:any, response:any, status:any, headers:any) => {
  //         console.log("VideoUpload:uploaded:", item,response, status);   
  //         //Notify Users
  //         this.addToast(response);
  //    };
  // }

  // //Notify Users
  // addToast(msg:string) {
  //   if(msg==""){
  //     msg = "No hay conneción al servidor!"
  //   }
  //   this.toastyService.info({
  //       title: "",
  //       msg: msg,
  //       showClose: true,
  //       timeout: 5000,
  //       theme: "material"
  //   });
  // };

}

