import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParadero } from '../paradero.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../paradero.test-samples';

import { ParaderoService } from './paradero.service';

const requireRestSample: IParadero = {
  ...sampleWithRequiredData,
};

describe('Paradero Service', () => {
  let service: ParaderoService;
  let httpMock: HttpTestingController;
  let expectedResult: IParadero | IParadero[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParaderoService);
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

    it('should create a Paradero', () => {
      const paradero = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paradero).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paradero', () => {
      const paradero = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paradero).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paradero', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paradero', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Paradero', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParaderoToCollectionIfMissing', () => {
      it('should add a Paradero to an empty array', () => {
        const paradero: IParadero = sampleWithRequiredData;
        expectedResult = service.addParaderoToCollectionIfMissing([], paradero);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paradero);
      });

      it('should not add a Paradero to an array that contains it', () => {
        const paradero: IParadero = sampleWithRequiredData;
        const paraderoCollection: IParadero[] = [
          {
            ...paradero,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParaderoToCollectionIfMissing(paraderoCollection, paradero);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paradero to an array that doesn't contain it", () => {
        const paradero: IParadero = sampleWithRequiredData;
        const paraderoCollection: IParadero[] = [sampleWithPartialData];
        expectedResult = service.addParaderoToCollectionIfMissing(paraderoCollection, paradero);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paradero);
      });

      it('should add only unique Paradero to an array', () => {
        const paraderoArray: IParadero[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paraderoCollection: IParadero[] = [sampleWithRequiredData];
        expectedResult = service.addParaderoToCollectionIfMissing(paraderoCollection, ...paraderoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paradero: IParadero = sampleWithRequiredData;
        const paradero2: IParadero = sampleWithPartialData;
        expectedResult = service.addParaderoToCollectionIfMissing([], paradero, paradero2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paradero);
        expect(expectedResult).toContain(paradero2);
      });

      it('should accept null and undefined values', () => {
        const paradero: IParadero = sampleWithRequiredData;
        expectedResult = service.addParaderoToCollectionIfMissing([], null, paradero, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paradero);
      });

      it('should return initial array if no Paradero is added', () => {
        const paraderoCollection: IParadero[] = [sampleWithRequiredData];
        expectedResult = service.addParaderoToCollectionIfMissing(paraderoCollection, undefined, null);
        expect(expectedResult).toEqual(paraderoCollection);
      });
    });

    describe('compareParadero', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParadero(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParadero(entity1, entity2);
        const compareResult2 = service.compareParadero(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParadero(entity1, entity2);
        const compareResult2 = service.compareParadero(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParadero(entity1, entity2);
        const compareResult2 = service.compareParadero(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
