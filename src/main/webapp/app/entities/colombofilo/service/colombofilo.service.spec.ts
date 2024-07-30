import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IColombofilo } from '../colombofilo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../colombofilo.test-samples';

import { ColombofiloService } from './colombofilo.service';

const requireRestSample: IColombofilo = {
  ...sampleWithRequiredData,
};

describe('Colombofilo Service', () => {
  let service: ColombofiloService;
  let httpMock: HttpTestingController;
  let expectedResult: IColombofilo | IColombofilo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ColombofiloService);
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

    it('should create a Colombofilo', () => {
      const colombofilo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(colombofilo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Colombofilo', () => {
      const colombofilo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(colombofilo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Colombofilo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Colombofilo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Colombofilo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addColombofiloToCollectionIfMissing', () => {
      it('should add a Colombofilo to an empty array', () => {
        const colombofilo: IColombofilo = sampleWithRequiredData;
        expectedResult = service.addColombofiloToCollectionIfMissing([], colombofilo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colombofilo);
      });

      it('should not add a Colombofilo to an array that contains it', () => {
        const colombofilo: IColombofilo = sampleWithRequiredData;
        const colombofiloCollection: IColombofilo[] = [
          {
            ...colombofilo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addColombofiloToCollectionIfMissing(colombofiloCollection, colombofilo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Colombofilo to an array that doesn't contain it", () => {
        const colombofilo: IColombofilo = sampleWithRequiredData;
        const colombofiloCollection: IColombofilo[] = [sampleWithPartialData];
        expectedResult = service.addColombofiloToCollectionIfMissing(colombofiloCollection, colombofilo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colombofilo);
      });

      it('should add only unique Colombofilo to an array', () => {
        const colombofiloArray: IColombofilo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const colombofiloCollection: IColombofilo[] = [sampleWithRequiredData];
        expectedResult = service.addColombofiloToCollectionIfMissing(colombofiloCollection, ...colombofiloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const colombofilo: IColombofilo = sampleWithRequiredData;
        const colombofilo2: IColombofilo = sampleWithPartialData;
        expectedResult = service.addColombofiloToCollectionIfMissing([], colombofilo, colombofilo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colombofilo);
        expect(expectedResult).toContain(colombofilo2);
      });

      it('should accept null and undefined values', () => {
        const colombofilo: IColombofilo = sampleWithRequiredData;
        expectedResult = service.addColombofiloToCollectionIfMissing([], null, colombofilo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colombofilo);
      });

      it('should return initial array if no Colombofilo is added', () => {
        const colombofiloCollection: IColombofilo[] = [sampleWithRequiredData];
        expectedResult = service.addColombofiloToCollectionIfMissing(colombofiloCollection, undefined, null);
        expect(expectedResult).toEqual(colombofiloCollection);
      });
    });

    describe('compareColombofilo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareColombofilo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareColombofilo(entity1, entity2);
        const compareResult2 = service.compareColombofilo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareColombofilo(entity1, entity2);
        const compareResult2 = service.compareColombofilo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareColombofilo(entity1, entity2);
        const compareResult2 = service.compareColombofilo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
