import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaloma } from '../paloma.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../paloma.test-samples';

import { PalomaService } from './paloma.service';

const requireRestSample: IPaloma = {
  ...sampleWithRequiredData,
};

describe('Paloma Service', () => {
  let service: PalomaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaloma | IPaloma[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PalomaService);
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

    it('should create a Paloma', () => {
      const paloma = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paloma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paloma', () => {
      const paloma = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paloma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paloma', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paloma', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Paloma', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPalomaToCollectionIfMissing', () => {
      it('should add a Paloma to an empty array', () => {
        const paloma: IPaloma = sampleWithRequiredData;
        expectedResult = service.addPalomaToCollectionIfMissing([], paloma);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paloma);
      });

      it('should not add a Paloma to an array that contains it', () => {
        const paloma: IPaloma = sampleWithRequiredData;
        const palomaCollection: IPaloma[] = [
          {
            ...paloma,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPalomaToCollectionIfMissing(palomaCollection, paloma);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paloma to an array that doesn't contain it", () => {
        const paloma: IPaloma = sampleWithRequiredData;
        const palomaCollection: IPaloma[] = [sampleWithPartialData];
        expectedResult = service.addPalomaToCollectionIfMissing(palomaCollection, paloma);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paloma);
      });

      it('should add only unique Paloma to an array', () => {
        const palomaArray: IPaloma[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const palomaCollection: IPaloma[] = [sampleWithRequiredData];
        expectedResult = service.addPalomaToCollectionIfMissing(palomaCollection, ...palomaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paloma: IPaloma = sampleWithRequiredData;
        const paloma2: IPaloma = sampleWithPartialData;
        expectedResult = service.addPalomaToCollectionIfMissing([], paloma, paloma2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paloma);
        expect(expectedResult).toContain(paloma2);
      });

      it('should accept null and undefined values', () => {
        const paloma: IPaloma = sampleWithRequiredData;
        expectedResult = service.addPalomaToCollectionIfMissing([], null, paloma, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paloma);
      });

      it('should return initial array if no Paloma is added', () => {
        const palomaCollection: IPaloma[] = [sampleWithRequiredData];
        expectedResult = service.addPalomaToCollectionIfMissing(palomaCollection, undefined, null);
        expect(expectedResult).toEqual(palomaCollection);
      });
    });

    describe('comparePaloma', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaloma(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaloma(entity1, entity2);
        const compareResult2 = service.comparePaloma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaloma(entity1, entity2);
        const compareResult2 = service.comparePaloma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaloma(entity1, entity2);
        const compareResult2 = service.comparePaloma(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
