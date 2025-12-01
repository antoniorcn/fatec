import EntityService from "./entityService";

export default class FaculdadeService extends EntityService {
    constructor() { 
        super('http://localhost:8080/api/faculdade/');
    }
}