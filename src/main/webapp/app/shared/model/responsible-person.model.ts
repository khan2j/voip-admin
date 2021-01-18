export interface IResponsiblePerson {
  id?: number;
  code?: string;
  firstName?: string;
  secondName?: string;
  lastName?: string;
  position?: string;
  room?: string;
  departmentName?: string;
  departmentId?: number;
}

export class ResponsiblePerson implements IResponsiblePerson {
  constructor(
    public id?: number,
    public code?: string,
    public firstName?: string,
    public secondName?: string,
    public lastName?: string,
    public position?: string,
    public room?: string,
    public departmentName?: string,
    public departmentId?: number
  ) {}
}
