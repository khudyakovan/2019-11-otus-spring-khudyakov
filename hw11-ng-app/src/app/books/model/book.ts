import {Author} from "../../authors/model/author";
import {Genre} from "../../genres/model/genre";

export class Book {
  id: string;
  title: string;
  isbn: string;
  publicationYear: string;
  authors: Author[];
  genres: Genre[];
  comments: Comment[];
}
