import { TestBed } from '@angular/core/testing';

import { SprintService } from './sprint.service';

describe('SprintServiceService', () => {
  let service: SprintService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SprintService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
