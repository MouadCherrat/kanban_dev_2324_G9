export class User {
name: any;
    constructor(
      public id: number,
      public username: string,
      public password: string,
      public email: string,
      public active: boolean,
      public roles: string
    ) {}
  }
  