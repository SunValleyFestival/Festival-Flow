export interface Association {
  shift_id: number;
  collaborator_id: number;
  status: number;
  request?: string | null;
  collaborator_friend_id?: number | null;
}
