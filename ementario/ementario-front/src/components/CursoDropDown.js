import React from 'react';
import { Dropdown } from 'primereact/dropdown';
import CursoService from '../service/CursoService';

class CursoDropDown extends React.Component { 

    service = new CursoService();
    
    state = { 
        entity : {},
        entities : []
    }

    
    componentDidMount() {
         this.service.getCursosByFaculdadeId(this.props.faculdadeId).then((data) => {
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
                        placeholder="Selecione o Curso"/>
        )
    }

}


export { CursoDropDown };