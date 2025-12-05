import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../../core/services/user.service';
import { DepartmentService } from '../../../../core/services/department.service';
import { UserDepartmentService } from '../../../../core/services/user-department.service';
import { UserResponse } from '../../../../core/models/user.model';
import { Department } from '../../../../core/models/department.model';
import { UserDepartment } from '../../../../core/models/user-department.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {
  users: UserResponse[] = [];
  departments: Department[] = [];
  userDepartments: Map<number, UserDepartment[]> = new Map();

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;

  isLoading = false;
  openDropdownUserId: number | null = null;
  searchTerm: string = '';

  get filteredUsers(): UserResponse[] {
    if (!this.searchTerm.trim()) {
      return this.users;
    }
    const term = this.searchTerm.toLowerCase();
    return this.users.filter(user =>
      user.firstName.toLowerCase().includes(term) ||
      user.lastName.toLowerCase().includes(term) ||
      user.email.toLowerCase().includes(term)
    );
  }

  constructor(
    private userService: UserService,
    private departmentService: DepartmentService,
    private userDepartmentService: UserDepartmentService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadDepartments();
    this.loadUsers();
  }

  loadDepartments(): void {
    this.departmentService.getActiveDepartments().subscribe({
      next: (response) => {
        this.departments = response.data;
      },
      error: (error) => console.error('Error loading departments:', error)
    });
  }

  loadUsers(): void {
    this.isLoading = true;
    this.userService.getAll(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.users = response.data.content;
        this.totalPages = response.data.totalPages;
        this.totalElements = response.data.totalElements;
        this.isLoading = false;
        // Load departments for each user
        this.users.forEach(user => this.loadUserDepartments(user.id));
      },
      error: (error) => {
        console.error('Error loading users:', error);
        this.isLoading = false;
      }
    });
  }

  loadUserDepartments(userId: number): void {
    this.userDepartmentService.getUserDepartments(userId).subscribe({
      next: (response) => {
        this.userDepartments.set(userId, response.data);
      },
      error: (error) => console.error(`Error loading departments for user ${userId}:`, error)
    });
  }

  toggleDepartmentDropdown(userId: number): void {
    this.openDropdownUserId = this.openDropdownUserId === userId ? null : userId;
  }

  isDepartmentAssigned(userId: number, departmentId: number): boolean {
    const userDepts = this.userDepartments.get(userId) || [];
    return userDepts.some(d => d.departmentId === departmentId);
  }

  toggleDepartment(userId: number, department: Department): void {
    if (this.isDepartmentAssigned(userId, department.id)) {
      // Remove department
      this.userDepartmentService.removeDepartment(userId, department.id).subscribe({
        next: () => {
          this.loadUserDepartments(userId);
          Swal.fire({
            icon: 'success',
            title: 'Departamento removido',
            timer: 1500,
            showConfirmButton: false
          });
        },
        error: (error) => console.error('Error removing department:', error)
      });
    } else {
      // Assign department
      this.userDepartmentService.assignDepartment(userId, {
        departmentId: department.id,
        canView: true,
        canEdit: true,
        canPublish: true
      }).subscribe({
        next: () => {
          this.loadUserDepartments(userId);
          Swal.fire({
            icon: 'success',
            title: 'Departamento asignado',
            timer: 1500,
            showConfirmButton: false
          });
        },
        error: (error) => console.error('Error assigning department:', error)
      });
    }
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadUsers();
  }

  createUser(): void {
    this.router.navigate(['/admin/users/new']);
  }

  editUser(id: number): void {
    this.router.navigate(['/admin/users/edit', id]);
  }

  deleteUser(user: UserResponse): void {
    Swal.fire({
      title: '¿Eliminar usuario?',
      text: `¿Está seguro de eliminar a "${user.firstName} ${user.lastName}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#dc2626',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.delete(user.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Eliminado',
              confirmButtonColor: '#1e40af',
              timer: 2000
            });
            this.loadUsers();
          }
        });
      }
    });
  }
}