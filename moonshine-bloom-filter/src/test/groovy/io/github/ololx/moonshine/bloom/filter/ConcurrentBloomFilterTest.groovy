package io.github.ololx.moonshine.bloom.filter
/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 18/12/2023 1:26 pm
 */

import spock.lang.Specification

/**
 * @author Alexander A. Kropotin
 *
 *     project moonshine
 *     created 24.11.2023 11:25
 */
class ConcurrentBloomFilterTest extends Specification {

    def "absent - when filter doesn't contain #bytesSupplierValue then return true"() {
        given:
        BloomFilter.HashFunction hashFunctionStub1 = (value) -> bits[0];
        BloomFilter.HashFunction hashFunctionStub2 = (value) -> bits[1];
        BloomFilter.BytesSupplier entry = () -> bytesSupplierValue
        def bloomFilter = BasicBloomFilter.newInstance(10, [hashFunctionStub1, hashFunctionStub2], 0)

        expect:
        bloomFilter.absent(entry)

        where:
        bytesSupplierValue | bits
        new byte[0]        | [3, 7] as int[]
    }

    def "add - when a new value is added then relevant bits are set"() {
        given:
        BloomFilter.HashFunction hashFunctionStub1 = (value) -> bits[0];
        BloomFilter.HashFunction hashFunctionStub2 = (value) -> bits[1];
        BloomFilter.BytesSupplier entry = () -> bytesSupplierValue
        def bloomFilter = BasicBloomFilter.newInstance(10, [hashFunctionStub1, hashFunctionStub2], 0)

        when:
        boolean result = bloomFilter.add(entry)

        then:
        result
        bloomFilter.bitStrategy.get(bits[0])
        bloomFilter.bitStrategy.get(bits[1])

        where:
        bytesSupplierValue | bits
        new byte[0]        | [3, 7] as int[]
    }

    def "absent - when value is present then return false"() {
        given:
        BloomFilter.HashFunction hashFunctionStub1 = (value) -> bits[0];
        BloomFilter.HashFunction hashFunctionStub2 = (value) -> bits[1];
        BloomFilter.BytesSupplier entry = () -> bytesSupplierValue
        def bloomFilter = BasicBloomFilter.newInstance(10, [hashFunctionStub1, hashFunctionStub2], 0)
        bloomFilter.bitStrategy.set(bits[0])
        bloomFilter.bitStrategy.set(bits[1])

        expect:
        !bloomFilter.absent(entry)

        where:
        bytesSupplierValue | bits
        new byte[0]        | [3, 7] as int[]
    }

    def "add - when adding values concurrently then all bits are correctly set"() {
        given:
        int size = 10
        BloomFilter.HashFunction hashFunctionStub = (value) -> Math.abs(Arrays.hashCode(value)) % size
        def bloomFilter = BasicBloomFilter.newInstance(size, [hashFunctionStub], 0)
        def entries = (0..20).collect {idx ->
            return {-> String.format("entry%d", idx).getBytes()} as BloomFilter.BytesSupplier
        }

        when:
        entries.parallelStream().forEach {bloomFilter.add(it)}

        then:
        entries.every {(!bloomFilter.absent(it))}
    }
}
