export interface DisciplineRequest {
  title: string;
  code?: string;
  syllabus?: string;
  complementaryContent?: string;
  keywords: string[];
  courseId: number;
}

export interface DisciplineResponse extends DisciplineRequest {
  id: number;
}

export interface CourseRequest {
  name: string;
  description?: string;
}

export interface CourseResponse extends CourseRequest {
  id: number;
  disciplines: DisciplineResponse[];
}

export interface CourseComparisonResponse {
  courseAId: number;
  courseBId: number;
  overlapScore: number;
  sharedKeywords: string[];
  exclusiveToCourseA: string[];
  exclusiveToCourseB: string[];
  relatedDisciplines: DisciplineRelationView[];
}

export interface DisciplineRelationView {
  disciplineAId: number;
  disciplineATitle: string;
  disciplineBId: number;
  disciplineBTitle: string;
  similarityScore: number;
  sharedKeywords: string[];
}
