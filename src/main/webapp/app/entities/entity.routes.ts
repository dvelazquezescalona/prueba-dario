import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'paradero',
    data: { pageTitle: 'pingeonControlApp.paradero.home.title' },
    loadChildren: () => import('./paradero/paradero.routes'),
  },
  {
    path: 'provincia',
    data: { pageTitle: 'pingeonControlApp.provincia.home.title' },
    loadChildren: () => import('./provincia/provincia.routes'),
  },
  {
    path: 'color',
    data: { pageTitle: 'pingeonControlApp.color.home.title' },
    loadChildren: () => import('./color/color.routes'),
  },
  {
    path: 'municipio',
    data: { pageTitle: 'pingeonControlApp.municipio.home.title' },
    loadChildren: () => import('./municipio/municipio.routes'),
  },
  {
    path: 'sociedad',
    data: { pageTitle: 'pingeonControlApp.sociedad.home.title' },
    loadChildren: () => import('./sociedad/sociedad.routes'),
  },
  {
    path: 'vuelo',
    data: { pageTitle: 'pingeonControlApp.vuelo.home.title' },
    loadChildren: () => import('./vuelo/vuelo.routes'),
  },
  {
    path: 'colombofilo',
    data: { pageTitle: 'pingeonControlApp.colombofilo.home.title' },
    loadChildren: () => import('./colombofilo/colombofilo.routes'),
  },
  {
    path: 'colombofilo-vuelo',
    data: { pageTitle: 'pingeonControlApp.colombofiloVuelo.home.title' },
    loadChildren: () => import('./colombofilo-vuelo/colombofilo-vuelo.routes'),
  },
  {
    path: 'paloma',
    data: { pageTitle: 'pingeonControlApp.paloma.home.title' },
    loadChildren: () => import('./paloma/paloma.routes'),
  },
  {
    path: 'premio',
    data: { pageTitle: 'pingeonControlApp.premio.home.title' },
    loadChildren: () => import('./premio/premio.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
