package chipyard.clocking

import chisel3._

import scala.collection.mutable.{ArrayBuffer}

import freechips.rocketchip.config.{Parameters, Field, Config}
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.regmapper._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.util._
import freechips.rocketchip.tile._
import freechips.rocketchip.prci._

// class ProgrammableClockDivider(address: BigInt, beatBytes: Int, params: TestChipClockDividerParams)(implicit p: Parameters, valName: ValName)
//     extends LazyModule {
//   val device = new SimpleDevice(s"${name}-clk-div", Nil)
//   val clockNode = ClockGroupIdentityNode()
//   //val resetNode = ClockSinkNode(Seq(ClockSinkParameters()))
//   val tlNode = TLRegisterNode(Seq(AddressSet(params.address, 4096-1)), device, "reg/control", beatBytes=beatBytes)
//   require (params.divBits <= 8)
//   lazy val module = new LazyModuleImp(this) {
//     require (clockNode.out.size == 1)
//     val sources = clockNode.in.head._1.member.data.toSeq
//     val sinks = clockNode.out.head._1.member.elements.toSeq
//     require (sources.size == sinks.size)
//     val nSinks = sinks.size
//     val initDivs = params.initDivs

//     val regs = (0 until nSinks) .map ({ i =>
//       val sinkName = sinks(i)._1
//       val tlDefaults = initDivs.filter(s => sinkName.contains(s._1)).map(_._2)
//       require(tlDefaults.size <= 1)
//       val tlDefault = if (tlDefaults.size > 0) tlDefaults(0) else 0
//       val asyncReset = sources(i).reset
//       val reg = withReset (asyncReset) {
//         Module(new AsyncResetRegVec(w=params.divBits, init=tlDefault))
//       }
//       if (tlDefaults.size > 0) {
//         println(s"ClockDiv for ${sinkName} regmapped at ${(params.address+i*4).toString(16)}, default ${tlDefault}")
//         sinks(i)._2.clock := withClockAndReset(sources(i).clock, asyncReset) {
//           val divider = Module(new testchipip.ClockDivideOrPass(params.divBits, depth = 3, genClockGate = p(ClockGateImpl)))
//           divider.io.divisor := reg.io.q
//           divider.io.resetAsync := ResetStretcher(sources(i).clock, asyncReset, 20).asAsyncReset
//           divider.io.clockOut
//         }
//       } else {
//         sinks(i)._2.clock := sources(i).clock
//       }
//       // Note this is not synchronized to the output clock, which takes time to appear
//       // so this is still asyncreset
//       sinks(i)._2.reset := ResetStretcher(sources(i).clock, asyncReset, 40).asAsyncReset
//       reg
//     })
//     tlNode.regmap((0 until nSinks).map({ i =>
//       i * 4 -> Seq(RegField.rwReg(params.divBits, regs(i).io)),
//     }): _*)
//   }
// }
