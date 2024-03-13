import {Component, OnInit} from '@angular/core';
import {CollaboratorService} from "../../../services/http/collaborator.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Collaborator} from "../../../interfaces/CollaboratorEntity";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  protected dataError: boolean = false
  protected collaborator: Collaborator = {} as Collaborator;

  constructor(
    private collaboratorService: CollaboratorService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.collaboratorService.getCollaboratorById(params['id']).subscribe((collaborator: Collaborator) => {
          this.collaborator = collaborator;
        });
      }
    });

  }

  submitData() {
    this.collaboratorService.updateCollaborator(this.collaborator).pipe().subscribe(() => {
      this.router.navigate(['admin/user']);
    });
  }

  deleteCollaborator() {
    this.collaboratorService.deleteCollaborator(this.collaborator).pipe().subscribe(() => {
      this.router.navigate(['admin/user']);
    });
  }
}
