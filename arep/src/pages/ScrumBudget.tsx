import React from 'react';
import LogoutButton from '../components/LogoutButton';

const ScrumBudget: React.FC = () => {
  return (
    <div style={{
      maxWidth: 800,
      margin: '40px auto',
      padding: 24,
      background: '#fff',
      borderRadius: 12,
      boxShadow: '0 10px 30px rgba(0,0,0,0.08)'
    }}>
      <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
        <LogoutButton />
      </div>
      <h1 style={{ marginTop: 0 }}>¿Cómo se producen los presupuestos en Scrum?</h1>
      <p>
        En Scrum, los presupuestos se derivan de la estimación ágil usando
        <strong> puntos de historia</strong>, la <strong>velocidad</strong> del equipo, y el
        <strong> costo por sprint</strong>, en lugar de planes detallados por adelantado.
      </p>
      <ol>
        <li>
          <strong>Estimar el esfuerzo en puntos de historia:</strong> Dividir el trabajo en historias de usuario y
          estimar usando dimensionamiento relativo (ej., Planning Poker).
        </li>
        <li>
          <strong>Medir la velocidad del equipo:</strong> Promedio de puntos de historia completados por sprint.
          Ejemplo: 40 puntos por sprint de 2 semanas.
        </li>
        <li>
          <strong>Calcular el costo por sprint:</strong> Incluir salarios, herramientas, gastos generales.
          Ejemplo: Costo del equipo $10,000 por sprint.
        </li>
        <li>
          <strong>Estimar el total de sprints:</strong> Puntos del backlog ÷ velocidad.
          Ejemplo: 400 puntos / 40 = 10 sprints.
        </li>
        <li>
          <strong>Producir el presupuesto:</strong> Presupuesto = costo por sprint × número de sprints.
          Ejemplo: $10,000 × 10 = <strong>$100,000</strong>.
        </li>
      </ol>
    </div>
  );
};

export default ScrumBudget;