export class FullJob {
  id: number;
  name: string;
  description: string;
  type: string;
  workplace: string;
  level: string;
  applies: number;
  companyName: string;
  location: string;
  searchLocations: Set<string>;
  saved: boolean;
  applied: boolean;
  listedAt: Date;
  modifiedAt: Date;
  expiredAt: Date;
  searchParamsId: Set<number>;

  constructor(
    id: number,
    name: string,
    description: string,
    type: string,
    workplace: string,
    level: string,
    applies: number,
    companyName: string,
    location: string,
    searchLocations: Set<string>,
    saved: boolean,
    applied: boolean,
    listedAt: Date,
    modifiedAt: Date,
    expiredAt: Date,
    searchParamsId: Set<number>
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.workplace = workplace;
    this.level = level;
    this.applies = applies;
    this.companyName = companyName;
    this.location = location;
    this.searchLocations = searchLocations;
    this.saved = saved;
    this.applied = applied;
    this.listedAt = listedAt;
    this.modifiedAt = modifiedAt;
    this.expiredAt = expiredAt;
    this.searchParamsId = searchParamsId;
  }
}

