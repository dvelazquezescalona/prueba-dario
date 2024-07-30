import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMunicipio } from '../municipio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../municipio.test-samples';

import { MunicipioService } from './municipio.service';

const requireRestSample: IMunicipio = {
  ...sampleWithRequiredData,
};

describe('Municipio Service', () => {
  let service: MunicipioService;
  let httpMock: HttpTestingController;
  let expectedResult: IMunicipio | IMunicipio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MunicipioService);
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

    it('should create a Municipio', () => {
      const municipio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(municipio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Municipio', () => {
      const municipio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(municipio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Municipio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Municipio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Municipio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMunicipioToCollectionIfMissing', () => {
      it('should add a Municipio to an empty array', () => {
        const municipio: IMunicipio = sampleWithRequiredData;
        expectedResult = service.addMunicipioToCollectionIfMissing([], municipio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(municipio);
      });

      it('should not add a Municipio to an array that contains it', () => {
        const municipio: IMunicipio = sampleWithRequiredData;
        const municipioCollection: IMunicipio[] = [
          {
            ...municipio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMunicipioToCollectionIfMissing(municipioCollection, municipio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Municipio to an array that doesn't contain it", () => {
        const municipio: IMunicipio = sampleWithRequiredData;
        const municipioCollection: IMunicipio[] = [sampleWithPartialData];
        expectedResult = service.addMunicipioToCollectionIfMissing(municipioCollection, municipio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(municipio);
      });

      it('should add only unique Municipio to an array', () => {
        const municipioArray: IMunicipio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const municipioCollection: IMunicipio[] = [sampleWithRequiredData];
        expectedResult = service.addMunicipioToCollectionIfMissing(municipioCollection, ...municipioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const municipio: IMunicipio = sampleWithRequiredData;
        const municipio2: IMunicipio = sampleWithPartialData;
        expectedResult = service.addMunicipioToCollectionIfMissing([], municipio, municipio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(municipio);
        expect(expectedResult).toContain(municipio2);
      });

      it('should accept null and undefined values', () => {
        const municipio: IMunicipio = sampleWithRequiredData;
        expectedResult = service.addMunicipioToCollectionIfMissing([], null, municipio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(municipio);
      });

      it('should return initial array if no Municipio is added', () => {
        const municipioCollection: IMunicipio[] = [sampleWithRequiredData];
        expectedResult = service.addMunicipioToCollectionIfMissing(municipioCollection, undefined, null);
        expect(expectedResult).toEqual(municipioCollection);
      });
    });

    describe('compareMunicipio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMunicipio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMunicipio(entity1, entity2);
        const compareResult2 = service.compareMunicipio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMunicipio(entity1, entity2);
        const compareResult2 = service.compareMunicipio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMunicipio(entity1, entity2);
        const compareResult2 = service.compareMunicipio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
