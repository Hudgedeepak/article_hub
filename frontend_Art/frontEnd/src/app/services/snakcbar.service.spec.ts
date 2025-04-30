import { TestBed } from '@angular/core/testing';

import { SnakcbarService } from './snakcbar.service';

describe('SnakcbarService', () => {
  let service: SnakcbarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SnakcbarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
