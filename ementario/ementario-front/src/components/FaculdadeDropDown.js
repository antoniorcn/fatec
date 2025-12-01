import React from 'react';
import { Dropdown } from 'primereact/dropdown';
import FaculdadeService from '../service/FaculdadeService';

class FaculdadeDropDown extends React.Component { 

    service = new FaculdadeService();
    
    state = { 
        entity : null,
        entities : []
    }

    
    componentDidMount() {
         this.service.getAll().then((data) => {
                const newState = {...this.state};
                newState.entities = data
                this.setState(newState);
            }
        );
    }

    updateEntity(entity) {
        const newState = {...this.state};
        newState.entity = entity;
        this.setState(newState);
        if (typeof(this.props.onChange) == 'function') {
            this.props.onChange(entity);
        }
    }

    render () { 
        console.log("Componente Renderizado");
        return (
            <Dropdown   optionLabel="nome" optionValue="id" value={this.state.entity} 
                        options={this.state.entities} 
                        onChange={(e) => this.updateEntity(e.value)} 
                        placeholder="Selecione a Faculdade"/>
        )
    }

}


export { FaculdadeDropDown };