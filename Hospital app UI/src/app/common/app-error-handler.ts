import { ErrorHandler, Injectable, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { LoggingService } from './Logging.Service';
import { NotificationService } from './Notification.Service';
import { ErrorService } from './Error.Service';
@Injectable()
export class AppErrorHandler implements ErrorHandler {
    constructor(private injector: Injector) { }
    handleError(error: Error | HttpErrorResponse){
        // alert("An Expected Error Occured. Please try after some time")
        const errorService = this.injector.get(ErrorService);
        const logger = this.injector.get(LoggingService);
        const notifier = this.injector.get(NotificationService);

        let message;
        let stackTrace;

        if (error instanceof HttpErrorResponse) {
            // Server Error
            message = errorService.getServerMessage(error);
            // stackTrace = errorService.getServerStack(error);
            notifier.showError(message);
        } else {
            // Client Error
            message = errorService.getClientMessage(error);
            stackTrace = errorService.getClientStack(error);
            notifier.showError(message);
        }
        // Always log errors
        logger.logError(message, stackTrace);
    }
}