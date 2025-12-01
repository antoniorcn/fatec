import React, { useState, useEffect } from 'react';
import { Chart } from 'primereact/chart';
import { ProductService } from '../service/ProductService';
import { EventService } from '../service/EventService';

const lineData = {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
        {
            label: 'Cursos',
            data: [65, 59, 80, 81, 56, 55, 40],
            fill: false,
            backgroundColor: '#2f4860',
            borderColor: '#2f4860'
        },
        {
            label: 'Disciplinas com Matches',
            data: [28, 48, 40, 19, 86, 27, 90],
            fill: false,
            backgroundColor: '#00bb7e',
            borderColor: '#00bb7e'
        }
    ]
};

export const Dashboard = () => {

    const [tasksCheckbox, setTasksCheckbox] = useState([]);
    const [events, setEvents] = useState(null);
    const [products, setProducts] = useState(null);

    useEffect(() => {
        const productService = new ProductService();
        const eventService = new EventService();
        productService.getProductsSmall().then(data => setProducts(data));
        eventService.getEvents().then(data => setEvents(data));
    }, []);

    return (
        <div className="p-grid p-fluid dashboard">
            <div className="p-col-12 p-lg-4">
                <div className="card summary">
                    <span className="title">Faculdades</span>
                    <span className="detail">Faculdades cadastradas</span>
                    <span className="count visitors">0</span>
                </div>
            </div>
            <div className="p-col-12 p-lg-4">
                <div className="card summary">
                    <span className="title">Cursos</span>
                    <span className="detail">Cursos cadastrados</span>
                    <span className="count purchases">0</span>
                </div>
            </div>
            <div className="p-col-12 p-lg-4">
                <div className="card summary">
                    <span className="title">Disciplinas</span>
                    <span className="detail">Disciplinas cadastradas</span>
                    <span className="count revenue">0</span>
                </div>
            </div>

            <div className="p-col-12 p-lg-12">
                <div className="card">
                    <Chart type="line" data={lineData} />
                </div>
            </div>
        </div>
    );
}
