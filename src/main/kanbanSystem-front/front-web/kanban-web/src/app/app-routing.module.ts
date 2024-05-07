import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './components/board-list/board-list.component';
import { HomeComponent } from './components/home/home.component';
import { TaskComponent } from './components/task/task.component';
import { SprintComponent } from './components/sprint/sprint.component';
import { AddSprintComponent } from './components/addsprint/addsprint.component';
import { AddboardComponent } from './components/addboard/addboard.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'boards', component: BoardComponent },
  { path: 'home', component: HomeComponent },
  { path: 'tasks/board/:boardId', component: TaskComponent },
  { path: 'sprints', component: SprintComponent },
  { path: 'addsprint', component: AddSprintComponent },
  { path: 'addboard', component: AddboardComponent },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
