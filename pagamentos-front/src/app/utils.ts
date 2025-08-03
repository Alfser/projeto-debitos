export class ObjectUtil{
    static cleanParams<T extends Object>(searchParams: T){
        Object.keys(searchParams).forEach(key => {
            if (searchParams[key as keyof T] == null) {
                delete searchParams[key as keyof T];
            }
        });
    }
}