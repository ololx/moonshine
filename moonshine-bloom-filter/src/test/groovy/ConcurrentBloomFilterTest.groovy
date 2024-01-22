/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 18/12/2023 1:26 pm
 */


import io.github.ololx.moonshine.bloom.filter.BloomFilter
import io.github.ololx.moonshine.bloom.filter.ConcurrentBloomFilter
import spock.lang.Specification

class ConcurrentBloomFilterTest extends Specification {

    def "absent - when filter doesn't contain #bytesSupplierValue then return true"() {
        given:
        BloomFilter.HashFunction hashFunctionStub1 = (value) -> bits[0];
        BloomFilter.HashFunction hashFunctionStub2 = (value) -> bits[1];
        BloomFilter.BytesSupplier entry = () -> bytesSupplierValue
        def bloomFilter = new ConcurrentBloomFilter(10, [hashFunctionStub1, hashFunctionStub2])

        expect:
        bloomFilter.absent(entry)

        where:
        bytesSupplierValue       | bits
        new byte[0]              | [3, 7] as int[]
    }
}
