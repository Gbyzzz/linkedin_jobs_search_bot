export enum UserRole {
  ADMIN = "ADMIN",
  USER = "USER"
}

export class UserProfile {
  private _chatId: number;
  private _username: string;
  private _botState: string;
  private _registeredAt: Date;
  private _userRole: UserRole;
  private _userPic: string;


  constructor(chatId: number, username: string, botState: string, registeredAt: Date, userRole: UserRole, userPic: string) {
    this._chatId = chatId;
    this._username = username;
    this._botState = botState;
    this._registeredAt = registeredAt;
    this._userRole = userRole;
    this._userPic = userPic;
  }


    get chatId(): number {
    return this._chatId;
  }

  set chatId(value: number) {
    this._chatId = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get botState(): string {
    return this._botState;
  }

  set botState(value: string) {
    this._botState = value;
  }

  get registeredAt(): Date {
    return this._registeredAt;
  }

  set registeredAt(value: Date) {
    this._registeredAt = value;
  }

  get userRole(): UserRole {
    return this._userRole;
  }

  set userRole(value: UserRole) {
    this._userRole = value;
  }

  get userPic(): string {
    return this._userPic;
  }

  set userPic(value: string) {
    this._userPic = value;
  }
}
