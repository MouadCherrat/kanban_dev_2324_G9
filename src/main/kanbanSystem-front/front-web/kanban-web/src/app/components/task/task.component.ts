import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Task } from 'src/app/models/Task';
import { TaskService } from 'src/app/services/task.service';
import { TaskStatus } from 'src/app/models/TaskStatus';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  tasks: Task[] = [];
  TaskStatus = TaskStatus;

  constructor(
    private router: Router, 
    private route: ActivatedRoute,
    private taskService: TaskService
  ) { }

  ngOnInit(): void {
    this.fetchTasks();
  }

  onDragStart(event: DragEvent, task: Task) {
    event.dataTransfer!.setData('text/plain', JSON.stringify(task));
  }

  onDrop(event: DragEvent, status: TaskStatus) {
    event.preventDefault();
    const taskId = JSON.parse(event.dataTransfer!.getData('text/plain')).id;
    this.updateTaskStatus(taskId, status);
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
  }

  filterTasks(status: TaskStatus): Task[] {
    return this.tasks.filter(task => task.status === status);
  }

  fetchTasks(): void {
    const boardId = this.route.snapshot.params['boardId'];
    this.taskService.getTasksByBoardId(boardId)
      .subscribe(
        (tasks: Task[]) => {
          this.tasks = tasks;
        },
        (error: any) => {
          console.error('Error fetching tasks: ', error);
        }
      );
  }

  updateTaskStatus(taskId: number, status: TaskStatus): void {
    this.taskService.updateTaskStatus(taskId, status)
      .subscribe(
        () => {
          this.fetchTasks();
        },
        (error: any) => {
          console.error('Error updating task status: ', error);
        }
      );
  }
}
