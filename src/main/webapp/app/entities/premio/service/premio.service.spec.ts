import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPremio } from '../premio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../premio.test-samples';

import { PremioService, RestPremio } from './premio.service';

const requireRestSample: RestPremio = {
  ...sampleWithRequiredData,
  fechaArribo: sampleWithRequiredData.fechaArribo?.toJSON(),
};

describe('Premio Service', () => {
  let service: PremioService;
  let httpMock: HttpTestingController;
  let expectedResult: IPremio | IPremio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PremioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Premio', () => {
      const premio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(premio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Premio', () => {
      const premio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(premio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Premio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Premio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Premio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPremioToCollectionIfMissing', () => {
      it('should add a Premio to an empty array', () => {
        const premio: IPremio = sampleWithRequiredData;
        expectedResult = service.addPremioToCollectionIfMissing([], premio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(premio);
      });

      it('should not add a Premio to an array that contains it', () => {
        const premio: IPremio = sampleWithRequiredData;
        const premioCollection: IPremio[] = [
          {
            ...premio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPremioToCollectionIfMissing(premioCollection, premio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Premio to an array that doesn't contain it", () => {
        const premio: IPremio = sampleWithRequiredData;
        const premioCollection: IPremio[] = [sampleWithPartialData];
        expectedResult = service.addPremioToCollectionIfMissing(premioCollection, premio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(premio);
      });

      it('should add only unique Premio to an array', () => {
        const premioArray: IPremio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const premioCollection: IPremio[] = [sampleWithRequiredData];
        expectedResult = service.addPremioToCollectionIfMissing(premioCollection, ...premioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const premio: IPremio = sampleWithRequiredData;
        const premio2: IPremio = sampleWithPartialData;
        expectedResult = service.addPremioToCollectionIfMissing([], premio, premio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(premio);
        expect(expectedResult).toContain(premio2);
      });

      it('should accept null and undefined values', () => {
        const premio: IPremio = sampleWithRequiredData;
        expectedResult = service.addPremioToCollectionIfMissing([], null, premio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(premio);
      });

      it('should return initial array if no Premio is added', () => {
        const premioCollection: IPremio[] = [sampleWithRequiredData];
        expectedResult = service.addPremioToCollectionIfMissing(premioCollection, undefined, null);
        expect(expectedResult).toEqual(premioCollection);
      });
    });

    describe('comparePremio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePremio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePremio(entity1, entity2);
        const compareResult2 = service.comparePremio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePremio(entity1, entity2);
        const compareResult2 = service.comparePremio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePremio(entity1, entity2);
        const compareResult2 = service.comparePremio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
