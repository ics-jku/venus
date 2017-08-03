package venus.riscv.insts.dsl

import venus.assembler.AssemblerError
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.formats.InstructionFormat
import venus.riscv.insts.dsl.impls.InstructionImplementation
import venus.riscv.insts.dsl.parsers.InstructionParser
import venus.riscv.insts.dsl.relocators.InstructionRelocator32
import venus.riscv.insts.dsl.relocators.InstructionRelocator64

open class Instruction(
        private val name: String,
        val format: InstructionFormat,
        val parser: InstructionParser,
        val impl32: InstructionImplementation,
        val impl64: InstructionImplementation,
        val relocator32: InstructionRelocator32,
        val relocator64: InstructionRelocator64
) {
    companion object {
        private val allInstructions = arrayListOf<Instruction>()

        operator fun get(mcode: MachineCode): Instruction =
                allInstructions.filter { it.format.length == mcode.length }
                        .firstOrNull { it.format.matches(mcode) }
                        ?: throw AssemblerError("instruction not found for $mcode")

        operator fun get(name: String) =
                allInstructions.firstOrNull { it.name == name }
                        ?: throw AssemblerError("instruction with name $name not found")
    }

    init {
        allInstructions.add(this)
    }
}