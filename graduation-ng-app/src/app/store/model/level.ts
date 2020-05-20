import { ParentLevel } from './parent-level';

export interface Level {
  id: string;
  name: string;
  parent: ParentLevel;
}
