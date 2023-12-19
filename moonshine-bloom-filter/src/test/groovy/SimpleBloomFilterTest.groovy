/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 18/12/2023 1:26 pm
 */


import io.github.ololx.moonshine.bloom.filter.BloomFilter
import io.github.ololx.moonshine.bloom.filter.SimpleBloomFilter
import spock.lang.Specification

class SimpleBloomFilterTest extends Specification {

    def "contains method should return false if any bit is not set"() {
        given: "a SimpleBloomFilter with one unset bit and mock hash functions"
        BloomFilter.HashFunction hashFunctionStub1 = (vv) -> 3;
        BloomFilter.HashFunction hashFunctionStub2 = (vv) -> 7;
        def entry = () -> byte[]::new
        def bloomFilter = new SimpleBloomFilter(10, [hashFunctionStub1, hashFunctionStub2])
        bloomFilter.bits.set(3)
        bloomFilter.bits.set(7)

        when: "checking if the filter contains an entry"
        def result = bloomFilter.contains(entry)

        then: "returns false"
        result
    }
}
