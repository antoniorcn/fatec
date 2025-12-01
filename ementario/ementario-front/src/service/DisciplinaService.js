import EntityService from "./EntityService";

export default class DisciplinaService extends EntityService {
    constructor() { 
        super('http://localhost:8080/api/disciplina/');
    }
}