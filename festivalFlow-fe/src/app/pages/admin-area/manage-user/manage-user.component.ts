import {Component, OnInit} from '@angular/core';
import {CollaboratorService} from "../../../services/http/admin/collaborator.service";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {
  protected collaborators: Collaborator[] = [];
  protected filteredCollaborators: Collaborator[] = [];
  protected nameToFilter: string = '';

  constructor(
    private collaboratorService: CollaboratorService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.collaboratorService.getCollaborators().pipe().subscribe((collaborators: Collaborator[]) => {
      this.collaborators = collaborators
      this.filteredCollaborators = collaborators
    });
  }

  goToDetailPage(id: number | undefined) {
    if (id) {
      this.router.navigate(['admin/user/' + id]);
    }
  }

  filterFirstName() {
    const searchTermList = this.nameToFilter.toLowerCase().trim().split(" ");

    this.filteredCollaborators = this.collaborators.filter(collaborator => (
      searchTermList.some((search) => collaborator.firstName?.toLowerCase().includes(search))||
      searchTermList.some((search) => collaborator.lastName?.toLowerCase().includes(search)) ||
      searchTermList.some((search) => collaborator.email?.toLowerCase().includes(search))||
      searchTermList.some((search) => collaborator.phone?.toLowerCase().includes(search))
    ));
  }
}
