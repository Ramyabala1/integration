export class ConsentRequest {
   
    
  
    timestamp:any;
    AuthorizationType:any;
    consent:{
        requester: {
            name: any;
            identifier:{
                type: any;
                value: any;
                system:any;
              } ;
          };
        permission: {
            accessMode: any;
            dateRange: {
                from: any;
                to: any;
                
              };
           
          };
        hiTypes:string[];
        hiu:{
            id:any;
        };
        hip:{
            id:any;
        };
        patient:{
            id:any;
        };
        purpose:{
            code: any;
            
          };
    
    };
  
}


 
 
 