import axios from 'axios';

export default class EntityService {

    basePath = '';

    constructor(basePath) { 
        this.basePath = basePath;
    }

    getAll() {
        return axios.get(this.basePath + 'getAll').then(res => res.data);
    }

    getById(id) {
      return axios.get(this.basePath + `getById/${id}`).then(res => res.data);
    }

    add(entidade) { 
        console.log("Adicionando entidade na API: ", entidade);
        const apiUrl = this.basePath + 'add';
        fetch(apiUrl, {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(entidade)
        });
    }

    save(entidade) { 
        console.log("Salvando entidade na API: ", entidade);
        const apiUrl = this.basePath + 'save';
        fetch(apiUrl, {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(entidade)
        });
    }    

    delete(entidade) { 
        console.log("Apagando entidade na API: ", entidade);
        const apiUrl = this.basePath + 'delete';
        fetch(apiUrl, {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(entidade)
        });
    }       

}