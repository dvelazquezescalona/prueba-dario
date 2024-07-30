import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IColombofiloVuelo } from '../colombofilo-vuelo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../colombofilo-vuelo.test-samples';

import { ColombofiloVueloService } from './colombofilo-vuelo.service';

const requireRestSample: IColombofiloVuelo = {
  ...sampleWithRequiredData,
};

describe('ColombofiloVuelo Service', () => {
  let service: ColombofiloVueloService;
  let httpMock: HttpTestingController;
  let expectedResult: IColombofiloVuelo | IColombofiloVuelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ColombofiloVueloService);
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

    it('should create a ColombofiloVuelo', () => {
      const colombofiloVuelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(colombofiloVuelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ColombofiloVuelo', () => {
      const colombofiloVuelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(colombofiloVuelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ColombofiloVuelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ColombofiloVuelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ColombofiloVuelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addColombofiloVueloToCollectionIfMissing', () => {
      it('should add a ColombofiloVuelo to an empty array', () => {
        const colombofiloVuelo: IColombofiloVuelo = sampleWithRequiredData;
        expectedResult = service.addColombofiloVueloToCollectionIfMissing([], colombofiloVuelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colombofiloVuelo);
      });

      it('should not add a ColombofiloVuelo to an array that contains it', () => {
        const colombofiloVuelo: IColombofiloVuelo = sampleWithRequiredData;
        const colombofiloVueloCollection: IColombofiloVuelo[] = [
          {
            ...colombofiloVuelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addColombofiloVueloToCollectionIfMissing(colombofiloVueloCollection, colombofiloVuelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ColombofiloVuelo to an array that doesn't contain it", () => {
        const colombofiloVuelo: IColombofiloVuelo = sampleWithRequiredData;
        const colombofiloVueloCollection: IColombofiloVuelo[] = [sampleWithPartialData];
        expectedResult = service.addColombofiloVueloToCollectionIfMissing(colombofiloVueloCollection, colombofiloVuelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colombofiloVuelo);
      });

      it('should add only unique ColombofiloVuelo to an array', () => {
        const colombofiloVueloArray: IColombofiloVuelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const colombofiloVueloCollection: IColombofiloVuelo[] = [sampleWithRequiredData];
        expectedResult = service.addColombofiloVueloToCollectionIfMissing(colombofiloVueloCollection, ...colombofiloVueloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const colombofiloVuelo: IColombofiloVuelo = sampleWithRequiredData;
        const colombofiloVuelo2: IColombofiloVuelo = sampleWithPartialData;
        expectedResult = service.addColombofiloVueloToCollectionIfMissing([], colombofiloVuelo, colombofiloVuelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(colombofiloVuelo);
        expect(expectedResult).toContain(colombofiloVuelo2);
      });

      it('should accept null and undefined values', () => {
        const colombofiloVuelo: IColombofiloVuelo = sampleWithRequiredData;
        expectedResult = service.addColombofiloVueloToCollectionIfMissing([], null, colombofiloVuelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(colombofiloVuelo);
      });

      it('should return initial array if no ColombofiloVuelo is added', () => {
        const colombofiloVueloCollection: IColombofiloVuelo[] = [sampleWithRequiredData];
        expectedResult = service.addColombofiloVueloToCollectionIfMissing(colombofiloVueloCollection, undefined, null);
        expect(expectedResult).toEqual(colombofiloVueloCollection);
      });
    });

    describe('compareColombofiloVuelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareColombofiloVuelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareColombofiloVuelo(entity1, entity2);
        const compareResult2 = service.compareColombofiloVuelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareColombofiloVuelo(entity1, entity2);
        const compareResult2 = service.compareColombofiloVuelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareColombofiloVuelo(entity1, entity2);
        const compareResult2 = service.compareColombofiloVuelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
