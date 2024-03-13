import {Component, OnInit} from '@angular/core';
import {CollaboratorService} from "../../../services/http/collaborator.service";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {
  protected collaborators: Collaborator[] = []

  constructor(
    private collaboratorService: CollaboratorService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.collaboratorService.getCollaborators().pipe().subscribe((collaborators: Collaborator[]) => {
      this.collaborators = collaborators
    });
  }

  goToDetailPage(id: number | undefined) {
    if (id) {
      this.router.navigate(['admin/user/' + id]);
    }
  }
}
