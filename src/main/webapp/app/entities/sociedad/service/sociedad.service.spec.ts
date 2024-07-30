import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISociedad } from '../sociedad.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sociedad.test-samples';

import { SociedadService } from './sociedad.service';

const requireRestSample: ISociedad = {
  ...sampleWithRequiredData,
};

describe('Sociedad Service', () => {
  let service: SociedadService;
  let httpMock: HttpTestingController;
  let expectedResult: ISociedad | ISociedad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SociedadService);
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

    it('should create a Sociedad', () => {
      const sociedad = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sociedad).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sociedad', () => {
      const sociedad = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sociedad).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sociedad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sociedad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Sociedad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSociedadToCollectionIfMissing', () => {
      it('should add a Sociedad to an empty array', () => {
        const sociedad: ISociedad = sampleWithRequiredData;
        expectedResult = service.addSociedadToCollectionIfMissing([], sociedad);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sociedad);
      });

      it('should not add a Sociedad to an array that contains it', () => {
        const sociedad: ISociedad = sampleWithRequiredData;
        const sociedadCollection: ISociedad[] = [
          {
            ...sociedad,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSociedadToCollectionIfMissing(sociedadCollection, sociedad);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sociedad to an array that doesn't contain it", () => {
        const sociedad: ISociedad = sampleWithRequiredData;
        const sociedadCollection: ISociedad[] = [sampleWithPartialData];
        expectedResult = service.addSociedadToCollectionIfMissing(sociedadCollection, sociedad);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sociedad);
      });

      it('should add only unique Sociedad to an array', () => {
        const sociedadArray: ISociedad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sociedadCollection: ISociedad[] = [sampleWithRequiredData];
        expectedResult = service.addSociedadToCollectionIfMissing(sociedadCollection, ...sociedadArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sociedad: ISociedad = sampleWithRequiredData;
        const sociedad2: ISociedad = sampleWithPartialData;
        expectedResult = service.addSociedadToCollectionIfMissing([], sociedad, sociedad2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sociedad);
        expect(expectedResult).toContain(sociedad2);
      });

      it('should accept null and undefined values', () => {
        const sociedad: ISociedad = sampleWithRequiredData;
        expectedResult = service.addSociedadToCollectionIfMissing([], null, sociedad, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sociedad);
      });

      it('should return initial array if no Sociedad is added', () => {
        const sociedadCollection: ISociedad[] = [sampleWithRequiredData];
        expectedResult = service.addSociedadToCollectionIfMissing(sociedadCollection, undefined, null);
        expect(expectedResult).toEqual(sociedadCollection);
      });
    });

    describe('compareSociedad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSociedad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSociedad(entity1, entity2);
        const compareResult2 = service.compareSociedad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSociedad(entity1, entity2);
        const compareResult2 = service.compareSociedad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSociedad(entity1, entity2);
        const compareResult2 = service.compareSociedad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
