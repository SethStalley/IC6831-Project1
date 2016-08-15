import {ToastyService, ToastyConfig} from 'ng2-toasty';
import { bootstrap }    from '@angular/platform-browser-dynamic';

import { App } from './app.component';
import { FileUploder } from './file-uploader.component';


bootstrap(App);
bootstrap(FileUploder, [
    ToastyService, ToastyConfig // It is required to have 1 unique instance of your service
]);

