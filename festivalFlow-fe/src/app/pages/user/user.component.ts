import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CollaboratorService} from "../../services/http/user/collaborator.service";
import {AssociationService} from "../../services/http/user/association.service";
import {Collaborator} from "../../interfaces/CollaboratorEntity";
import {Association} from "../../interfaces/AssociationEntity";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  protected activeUser: Collaborator = {} as Collaborator;
  protected associations: Association[] = [];

  constructor(
    private route: ActivatedRoute,
    private collaboratorService: CollaboratorService,
    private associationService: AssociationService,
  ) {
  }

  ngOnInit() {
    this.collaboratorService.getCollaboratorFromToken().subscribe((collaborator: Collaborator) => {
      this.activeUser = collaborator;
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.associationService.getAssociationByCollaboratorId(params['id']).subscribe((associations: any[]) => {
          this.associations = associations;
        });
      }
    });
  }

}
