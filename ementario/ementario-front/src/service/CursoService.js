import EntityService from "./EntityService";
import axios from 'axios';

export default class FaculdadeService extends EntityService {
    constructor() { 
        super('http://localhost:8080/api/curso/');
    }

    getCursosByFaculdadeId(faculdadeId) {
        return axios.get(this.basePath + `/getAllByFaculdade/${faculdadeId}`).then(res => res.data);
    }
}