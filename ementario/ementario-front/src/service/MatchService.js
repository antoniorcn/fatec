import EntityService from "./EntityService";

export default class MatchService extends EntityService {
    constructor() { 
        super('http://localhost:8080/api/match/');
    }
}